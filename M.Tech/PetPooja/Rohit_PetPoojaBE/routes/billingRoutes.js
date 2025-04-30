const express = require('express');
const router = express.Router();
const db = require('../config/db');

// Place an order
router.post('/', async (req, res) => {
    const { table_id, items } = req.body;

    // Get active session
    const [session] = await db.query(
        'SELECT * FROM table_sessions WHERE table_id = ? AND status = "OPEN"',
        [table_id]
    );

    if (session.length === 0) {
        return res.status(400).send('❌ No active session for this table.');
    }

    const session_id = session[0].id;

    // Check if an open order exists
    const [existingOrders] = await db.query(
        'SELECT id, total_price FROM orders WHERE table_id = ? AND session_id = ? AND status = "OPEN"',
        [table_id, session_id]
    );

    let order_id;
    let currentTotalPrice = 0;

    if (existingOrders.length > 0) {
        order_id = existingOrders[0].id;
        currentTotalPrice = parseFloat(existingOrders[0].total_price) || 0;
    } else {
        const [orderResult] = await db.query(
            'INSERT INTO orders (table_id, session_id, status, total_price) VALUES (?, ?, "OPEN", 0)',
            [table_id, session_id]
        );
        order_id = orderResult.insertId;
    }

    let newTotalPrice = 0;

    //Process each item
    for (const item of items) {
        const [menuItem] = await db.query('SELECT * FROM menu_items WHERE id = ?', [item.menu_item_id]);
        if (menuItem.length === 0) {
            return res.status(404).send('❌ Menu Item not found');
        }

        const price = menuItem[0].price;
        const stockAvailable = menuItem[0].stock_quantity;

        if (stockAvailable < item.quantity) {
            return res.status(400).send(`❌ Not enough stock for ${menuItem[0].name}. Only ${stockAvailable} left.`);
        }

        newTotalPrice += price * item.quantity;

        await db.query('INSERT INTO order_items (order_id, menu_item_id, quantity) VALUES (?, ?, ?)',
            [order_id, item.menu_item_id, item.quantity]);

        await db.query('UPDATE menu_items SET stock_quantity = stock_quantity - ? WHERE id = ?',
            [item.quantity, item.menu_item_id]);

        await db.query('INSERT INTO inventory_updates (menu_item_id, quantity_changed) VALUES (?, ?)',
            [item.menu_item_id, -item.quantity]);
    }

    const updatedTotalPrice = currentTotalPrice + newTotalPrice;
    await db.query('UPDATE orders SET total_price=? WHERE id=?', [updatedTotalPrice, order_id]);

    res.send({ order_id, total_price: newTotalPrice });
}); 

// Generate final bill by session_id
router.get('/bill-by-session/:session_id', async (req, res) => {
    const session_id = req.params.session_id;

    const [orders] = await db.query(
        'SELECT id FROM orders WHERE session_id = ?',
        [session_id]
    );

    if (orders.length === 0) {
        return res.status(404).send('❌ No orders found for this session');
    }

    const orderIds = orders.map(o => o.id);

    const [items] = await db.query(`
        SELECT mi.name, oi.quantity, mi.price
        FROM order_items oi
        JOIN menu_items mi ON oi.menu_item_id = mi.id
        WHERE oi.order_id IN (?)
    `, [orderIds]);

    let finalTotal = 0;
    const detailedItems = items.map(item => {
        const total = item.price * item.quantity;
        finalTotal += total;
        return {
            name: item.name,
            price: item.price,
            quantity: item.quantity,
            itemTotal: total
        };
    });

    res.json({
        session_id,
        items: detailedItems,
        finalTotal
    });
});

module.exports = router;

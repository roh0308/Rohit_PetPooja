const express = require('express');
const router = express.Router();
const db = require('../config/db');

router.get('/', async (req, res) => {
    try {
        const period = req.query.period || 'daily'; // default daily

        let salesQuery = '';
        let ordersQuery = '';

        if (period === 'daily') {
            salesQuery = `
                SELECT SUM(total_price) as total_sales
                FROM orders
                WHERE status = "CLOSED" AND DATE(order_date) = CURDATE()
            `;
            ordersQuery = `
                SELECT COUNT(id) as total_orders
                FROM orders
                WHERE status = "CLOSED" AND DATE(order_date) = CURDATE()
            `;
        } else if (period === 'monthly') {
            salesQuery = `
                SELECT SUM(total_price) as total_sales
                FROM orders
                WHERE status = "CLOSED" AND MONTH(order_date) = MONTH(CURDATE())
                  AND YEAR(order_date) = YEAR(CURDATE())
            `;
            ordersQuery = `
                SELECT COUNT(id) as total_orders
                FROM orders
                WHERE status = "CLOSED" AND MONTH(order_date) = MONTH(CURDATE())
                  AND YEAR(order_date) = YEAR(CURDATE())
            `;
        } else if (period === 'yearly') {
            salesQuery = `
                SELECT SUM(total_price) as total_sales
                FROM orders
                WHERE status = "CLOSED" AND YEAR(order_date) = YEAR(CURDATE())
            `;
            ordersQuery = `
                SELECT COUNT(id) as total_orders
                FROM orders
                WHERE status = "CLOSED" AND YEAR(order_date) = YEAR(CURDATE())
            `;
        } else {
            return res.status(400).send('Invalid period. Use daily, monthly, or yearly.');
        }

        // Fetch sales
        const [salesResult] = await db.query(salesQuery);
        const sales = salesResult[0]?.total_sales || 0;

        // Fetch orders
        const [ordersResult] = await db.query(ordersQuery);
        const orders = ordersResult[0]?.total_orders || 0;

        // Fetch stock (always)
        const [stock] = await db.query(`
            SELECT name, stock_quantity
            FROM menu_items
        `);

        res.json({
            period: period,
            sales: sales,
            orders: orders,
            stock: stock
        });

    } catch (error) {
        console.error('Dashboard error:', error);
        res.status(500).send('Internal Server Error');
    }
});

// Sales by item - with filter daily, monthly, yearly
router.get('/sales-by-item', async (req, res) => {
    const filter = req.query.filter || 'daily';

    let dateCondition = '';

    if (filter === 'daily') {
        dateCondition = "DATE(o.order_date) = CURDATE()";
    } else if (filter === 'monthly') {
        dateCondition = "MONTH(o.order_date) = MONTH(CURDATE()) AND YEAR(o.order_date) = YEAR(CURDATE())";
    } else if (filter === 'yearly') {
        dateCondition = "YEAR(o.order_date) = YEAR(CURDATE())";
    }

    const [items] = await db.query(`
        SELECT 
            mi.name AS item_name,
            SUM(oi.quantity) AS total_sold
        FROM order_items oi
        JOIN orders o ON oi.order_id = o.id
        JOIN menu_items mi ON oi.menu_item_id = mi.id
        WHERE ${dateCondition} AND o.status = "CLOSED"
        GROUP BY oi.menu_item_id, mi.name
        ORDER BY total_sold DESC
    `);

    res.json(items);
});


module.exports = router;

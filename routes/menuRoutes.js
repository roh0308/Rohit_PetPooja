const express = require('express');
const router = express.Router();
const db = require('../config/db');

// Add Menu Item
router.post('/', async (req, res) => {
    const { name, price, stock_quantity } = req.body;
    await db.query('INSERT INTO menu_items (name, price, stock_quantity) VALUES (?, ?, ?)', 
        [name, price, stock_quantity]);
    res.send('Menu item added');
});

// Get Menu Items
router.get('/', async (req, res) => {
    const [rows] = await db.query('SELECT * FROM menu_items');
    res.json(rows);
});

// Update Menu Item
router.put('/:id', async (req, res) => {
    const { name, price, stock_quantity } = req.body;
    await db.query('UPDATE menu_items SET name=?, price=?, stock_quantity=? WHERE id=?', 
        [name, price, stock_quantity, req.params.id]);
    res.send('Menu item updated');
});

// Delete Menu Item
router.delete('/:id', async (req, res) => {
    await db.query('DELETE FROM menu_items WHERE id=?', [req.params.id]);
    res.send('Menu item deleted');
});

module.exports = router;

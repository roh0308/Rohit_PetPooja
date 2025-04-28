const express = require('express');
const router = express.Router();
const db = require('../config/db');

// Start a new session for a table
router.post('/start-session/:table_id', async (req, res) => {
    const table_id = req.params.table_id;

    const [existing] = await db.query(
        'SELECT * FROM table_sessions WHERE table_id = ? AND status = "OPEN"',
        [table_id]
    );

    if (existing.length > 0) {
        return res.status(400).send('❌ Session already open for this table.');
    }

    await db.query('INSERT INTO table_sessions (table_id) VALUES (?)', [table_id]);
    res.send('✅ Session started.');
});

// Get tables with open sessions and their total open bill
router.get('/open-sessions', async (req, res) => {
    const [sessions] = await db.query(`
        SELECT 
            ts.id,
            ts.table_id,
            t.table_number,
            COALESCE(SUM(o.total_price), 0) as total_open_bill
        FROM table_sessions ts
        JOIN tables t ON ts.table_id = t.id
        LEFT JOIN orders o ON ts.id = o.session_id AND o.status = 'OPEN'
        WHERE ts.status = 'OPEN'
        GROUP BY ts.id, ts.table_id, t.table_number
    `);
    res.json(sessions);
});



// Close session for a table
router.put('/close-session/:table_id', async (req, res) => {
    const table_id = req.params.table_id;

    const [session] = await db.query(
        'SELECT * FROM table_sessions WHERE table_id = ? AND status = "OPEN"',
        [table_id]
    );

    if (session.length === 0) {
        return res.status(400).send('❌ No open session for this table.');
    }

    const session_id = session[0].id;

    // Close all orders linked to this session
    await db.query('UPDATE orders SET status="CLOSED" WHERE session_id = ?', [session_id]);

    // Close the session
    await db.query('UPDATE table_sessions SET status="CLOSED", closed_time=NOW() WHERE id = ?', [session_id]);

    res.send({ message: '✅ Session closed successfully', session_id });
});

module.exports = router;

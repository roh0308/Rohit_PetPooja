const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');

const menuRoutes = require('./routes/menuRoutes');
const tableRoutes = require('./routes/tableRoutes');
const billingRoutes = require('./routes/billingRoutes');
const dashboardRoutes = require('./routes/dashboardRoutes');

const app = express();
// Allow CORS from frontend (localhost:3000)
app.use(cors({
    origin: 'http://localhost:3000'
  }));
app.use(bodyParser.json());

app.use('/menu', menuRoutes);
app.use('/table', tableRoutes);
app.use('/order', billingRoutes);
app.use('/dashboard', dashboardRoutes);

app.listen(3001, () => {
    console.log('Server running on http://localhost:3001');
});

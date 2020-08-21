const mariadb = require('mariadb');
const config = require('./config.js');

const pool = mariadb.createPool({
    host: config.host, port: config.port,
    user: config.user, password:config.password,
    database: config.database,
    connectionLimit:5
});

function dbHelper() {
    this.getConnection = function(callback) {
         pool.getConnection()
            .then(conn => { callback(conn); 
        }).catch(err => { }); };
    this.getConnectionAsync = async function() { 
            try { 
                var conn = await pool.getConnection(); // console.log("conn = " + conn); // { affectedRows: 1, insertId: 1, warningStatus: 0 }
                return conn;
                } catch (err){
                    throw err;
                }
                return null;
            };
    this.sendJSON = function(response, httpCode, body) {
        var result = JSON.stringify(body); response.send(httpCode, result);
    };
}
module.exports = new dbHelper();
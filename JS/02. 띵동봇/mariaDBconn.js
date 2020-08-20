const mariadb = requrie('mariadb');
const config = requrie('./config.js');

const pool = mariadb.createPool({
    host: config.host, port: config.port,
    user: config.user, password:config.password,
    database: config.database,
    connectionLimit:5
});

async function GetUserList(){
    var conn, rows;
    try{
        conn = await pool.getConnection();
        conn.query('USE thinkthink_bot');
        rows = await conn.query('SELECT * FROM table');
    }
    catch(err){throw err;}

    finally{
        if(conn){conn.end(); return rows[0];}
    }

}

module.exports = {getUserList: GetUserList}
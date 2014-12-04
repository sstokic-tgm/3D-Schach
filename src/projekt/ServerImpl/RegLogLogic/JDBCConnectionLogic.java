package projekt.ServerImpl.RegLogLogic;

import com.esotericsoftware.kryonet.rmi.ObjectSpace;
import com.mysql.jdbc.jdbc2.optional.*;

import java.io.*;
import java.sql.*;
import java.util.logging.*;

import projekt.ServerImpl.Packets.AuthenticationPacket;

/**
 * This class implements the connection logic for the database. It allows connecting, disconnecting, registering
 * and login the user
 * 
 * @author Stokic Stefan
 * @version 1.0
 */
public class JDBCConnectionLogic {

	private final String host = "localhost";
	private final String user = "3dschach";
	private final String pass = "projekt_3d_schach";
	private final String db = "3DSchach";

	private MysqlDataSource ds;
	private Connection con;
	private ResultSet rs;
	private PreparedStatement st;
	private boolean status = false;

	private final static Logger log = Logger.getLogger("JDBCConnectionLogic");
	private static boolean hasFileHandler = false;


	/**
	 * Setup the JDBCConnectionLogic logger
	 */
	public JDBCConnectionLogic() {
		
		final String logV = "./logs/jdbcconnectionlogic.log";

		if(!hasFileHandler)
		{
			try {

				File f = new File(logV);

				if(!f.exists()){

					f.getParentFile().mkdirs();
					f.createNewFile();

				}

				FileHandler fh = new FileHandler(logV, true);
				SimpleFormatter sf = new SimpleFormatter();

				fh.setFormatter(sf);
				log.addHandler(fh);

				log.log(Level.INFO, "JDBCConnectionLogic logger started!");
				hasFileHandler = true;

			}catch(IOException ioe){
				log.log(Level.SEVERE, "IOException: " + ioe.getMessage());
			}
		}
		
		connect();
	}

	/**
	 * Connect to the database
	 * 
	 * @return if the connection was successfull or not
	 */
	private boolean connect() {

		this.ds = new MysqlDataSource();

		this.ds.setServerName(this.getHost());
		this.ds.setUser(this.getUser());
		this.ds.setPassword(this.getPass());
		this.ds.setDatabaseName(this.getDB());

		try {

			this.con = this.ds.getConnection();

			log.log(Level.INFO, "Successfully connected to the database!");
			return this.status = true;

		}catch(SQLException sqle) {

			log.log(Level.SEVERE, "Error: " + sqle.getMessage());
			return this.status = false;
		}
	}

	/**
	 * Disconnect from the database
	 */
	public void disconnect() {

		if(this.status == true) {

			try {

				this.con.close();

				if(st != null && rs != null) {

					this.st.close();
					this.rs.close();
					this.st = null;
					this.rs = null;
				}

				this.con = null;

				log.log(Level.INFO, "Successfully disconnected from the database!");
				this.status = false;

			}catch(SQLException sqle) {

				log.log(Level.SEVERE, "Error: " + sqle.getMessage());
				this.status = true;
			}
		}
	}

	/**
	 * Execute the login query
	 * 
	 * @param sql the login query
	 * @return result of the query
	 */
	private ResultSet executeLoginQuery(String sql) {

		try {

			this.st = con.prepareStatement(sql);
			this.rs = st.executeQuery();

			log.log(Level.INFO, "Successfully executed method: executeLoginQuery()");
			return this.rs;

		}catch(SQLException sqle) {

			log.log(Level.SEVERE, "Error: " + sqle.getMessage());
			return null;
		}
	}

	/**
	 * Execute register query
	 * 
	 * @param sql the register query
	 * @return result of the query
	 */
	private int executeRegisterQuery(String sql) {

		int ret;

		try {

			this.st = con.prepareStatement(sql);
			ret = st.executeUpdate();

			log.log(Level.INFO, "Successfully executed method: executeRegisterQuery()");
			return ret;

		}catch(SQLException sqle) {

			log.log(Level.SEVERE, "Error: " + sqle.getMessage());
			return 0;
		}
	}

	/**
	 * Login the user
	 * 
	 * @param username
	 * @param password
	 * @return if logged in or not
	 */
	public boolean login(String username, String password) {

		final String sql = "SELECT Username FROM Users WHERE Username = '" + username + "' AND Password = '" + password + "';";

		String user = "";
		boolean found = false;

		try {

			ResultSet rs = this.executeLoginQuery(sql);

			if(rs.next()) {

				user = rs.getString(1);
				found = true;
			}

			if(user.isEmpty() || user.equals(null) || found == false)
				return found;

			rs.beforeFirst();
			log.log(Level.INFO, user + " logged in!");
			return found;

		}catch(SQLException sqle) {

			log.log(Level.SEVERE, "Error: " + sqle.getMessage());
			return found;
		}
	}

	/**
	 * Register the user
	 * 
	 * @param username
	 * @param password
	 * @return if registered or not
	 */
	public boolean register(String username, String password) {

		final String sql = "INSERT INTO Users (Username, Password) VALUES('" + username + "', '" + password + "');";

		boolean succeed = false;
		int ret = this.executeRegisterQuery(sql);

		if(ret > 0) {

			log.log(Level.INFO, username + " successfully registered!");
			succeed = true;

		}else {

			log.log(Level.SEVERE, "Error: " + username + " registration failed!");
			succeed = false;
		}

		return succeed;
	}

	/**
	 * Receive the hostname
	 * 
	 * @return the hostname
	 */
	private String getHost() {

		return this.host;
	}

	/**
	 * Receive the username
	 * 
	 * @return the username
	 */
	private String getUser() {

		return this.user;
	}

	/**
	 * Receive the password
	 * 
	 * @return the password
	 */
	private String getPass() {

		return this.pass;
	}

	/**
	 * Receive the database name
	 * 
	 * @return the database name
	 */
	private String getDB() {

		return this.db;
	}

	/**
	 * Receive the current status of the database connection
	 * 
	 * @return the current status of the database connection
	 */
	public boolean getStatus() {

		return this.status;
	}

	/**
	 * Set the current status of the database connection
	 * 
	 * @param the current status of the database connection
	 */
	public void setStatus(boolean status) {

		this.status = status;
	}
}

package pl.java.jdbc.db;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DbUtil {
	private static DbUtil dbUtil;
	
	//obiekt C3PO odpowiedzialny za zadzadzanie po³aczeniami
	private ComboPooledDataSource connectionPool;
	
	private DbUtil() throws PropertyVetoException {
		connectionPool = new ComboPooledDataSource();
		//podstawowe inforacje o bazie danych
		connectionPool.setDriverClass("com.mysql.jdbc.Driver");
		connectionPool.setJdbcUrl("jdbc:mysql://localhost:3306/world");
		connectionPool.setUser("root");
		connectionPool.setPassword("password");
		
		//konfigurowanie puli po³aczen
		//poczatkowa liczba po³aczen
		connectionPool.setInitialPoolSize(5);
		//..min dostepna liczba podtzymywanych po³aczen
		connectionPool.setMinPoolSize(5);
		//max liczba podtrzymwywanych po³aczen
		connectionPool.setMaxPoolSize(20);
		//ilosc oddatkowych po³aczen ktora ma byc otworzona gdy wszytkie sa zjaete
		connectionPool.setAcquireIncrement(5);
		connectionPool.setMaxIdleTime(3600);
	}
	
	public Connection getConnection() throws SQLException {
		return connectionPool.getConnection();
	}
	
	public void close() {
		connectionPool.close();
	}
	
	public static DbUtil getInstance() {
		if (dbUtil == null) {
			try {
				dbUtil = new DbUtil();
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}			
		}
		return dbUtil;
	}

}

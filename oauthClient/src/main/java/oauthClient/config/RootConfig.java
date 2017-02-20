package oauthClient.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mysql.jdbc.Driver;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages={"oauthClient"}
	,excludeFilters={@Filter(type=FilterType.ANNOTATION,value={EnableWebMvc.class})})
public class RootConfig {
	
	private static String DRIVER_CLASS_NAME="com.mysql.jdbc.Driver";
	private static String PASSWORD="";
	private static String USERNAME="root";
	private static String URI="jdbc:mysql://127.0.0.1:3306/oauth_client";

	public RootConfig() {
		// TODO Auto-generated constructor stub
	}

	@Bean
	public DataSource dataSource(){
		BasicDataSource ds=new BasicDataSource();
			ds.setDriverClassName(DRIVER_CLASS_NAME);
			ds.setPassword(PASSWORD);
			ds.setUsername(USERNAME);
			ds.setUrl(URI);
			return ds;
		
	}
	
	@Bean
	public LocalSessionFactoryBean sessionFactory(){
		LocalSessionFactoryBean lsfb=new LocalSessionFactoryBean();
			lsfb.setDataSource(dataSource());
			
			Properties ps=new Properties();
				ps.setProperty("hibernate.show_sql", "true");
				ps.setProperty("hibernate.format_sql", "true");
				ps.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
			lsfb.setHibernateProperties(ps);
			lsfb.setPackagesToScan("oauthServer.model");
			//lsfb.setAnnotatedPackages("oauthServer.model");
		return lsfb;
	}
	
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory){
		HibernateTransactionManager tm=new  HibernateTransactionManager(sessionFactory);
		return tm;
	}
}

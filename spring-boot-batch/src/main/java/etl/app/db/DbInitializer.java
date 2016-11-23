package etl.app.db;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

@Component
public class DbInitializer implements InitializingBean {

    private final DataSource dataSource;

    @Autowired
    public DbInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Resource resource = new ClassPathResource("/sql/init.sql");
        runScript(resource);
    }

    private void runScript(Resource resource) throws Exception {
   
    	JdbcTemplate template = new JdbcTemplate(dataSource);
    	String sql = FileUtils.readFileToString(resource.getFile());
    	List<String> s = new ArrayList<String>();
    	ScriptUtils.splitSqlScript(sql, ";", s);
    	for(String query:s){
    		template.execute(query);  
    	}
    	      
    }
}

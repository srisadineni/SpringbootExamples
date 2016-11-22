package etl.app.processor;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import etl.app.domin.BaseTo;


public class EtlItemProcessor implements
		ItemProcessor<BaseTo, BaseTo> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private static final String ROW_DELIMITER = "@;@";
	private static final String ENTITY_DELIMITER = "@|@";
	private static final String COLUMN_DELIMITER = "@^@";
	private static final String SPLIT_DELIMITER = ",";

	@Autowired
	private DataSource dataSource;

	@Override
	public BaseTo process(BaseTo originalBaseTo) {
		
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			
			//The ideal way of getting the section information is to store the section information in a table.
			/*
			 List<SectionTo> sectionList = jdbcTemplate.query("SELECT * FROM SECTION_ORDER ORDER BY SECTION_ID", new BeanPropertyRowMapper<SectionTo>());
			 Set<SectionTo> sections = new TreeSet<SectionTo>(sectionList);
			*/
			
			//Temporary solution to run the poc is to get the section information from properties file.
			String[]  sections= originalBaseTo.getDbViewList().split(SPLIT_DELIMITER);
		
			for(String section:sections){
			  addSection(originalBaseTo, jdbcTemplate,section );
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return originalBaseTo;
	}

	private void addSection(BaseTo originalBaseTo, JdbcTemplate jdbcTemplate, String section) {
		List<BaseTo> memElgList = getSectionList(originalBaseTo, jdbcTemplate, section);
		String sectionText = "";
		for(BaseTo baseTo: memElgList){
			sectionText = sectionText==""?baseTo.getText():ROW_DELIMITER+baseTo.getText();
		}
		originalBaseTo.setText(originalBaseTo.getText()+ENTITY_DELIMITER+sectionText);
	}

	private List<BaseTo> getSectionList(BaseTo originalBaseTo,
			JdbcTemplate jdbcTemplate, String tableName) {
		return jdbcTemplate.query("SELECT * FROM " + tableName + " where EMPLOYEE_ID = "+originalBaseTo.getEmployeeId(), new BeanPropertyRowMapper<BaseTo>() {
			@Override
			public BaseTo mapRow(ResultSet rs, int rowNumber)
					throws SQLException {
				BaseTo baseTo = new BaseTo();

				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				baseTo.setEmployeeId(rs.getLong("EMPLOYEE_ID"));
				for (int index = 1; index <= columnCount; index++) {
					String value = rs.getString(index);
					value = value == null ? "" : value;
					if (baseTo != null && baseTo.getText() != null) {
						value = COLUMN_DELIMITER + value;
					}
					baseTo.setText(baseTo.getText() == null ? value : baseTo
							.getText() + value);

				}

				return baseTo;
			}	
		});
	}
}

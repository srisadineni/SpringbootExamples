package etl.app;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import etl.app.domin.BaseTo;
import etl.app.partition.RangePartitioner;
import etl.app.processor.EtlItemProcessor;

@Configuration
@EnableBatchProcessing
@EnableJpaRepositories
public class BatchConfiguration {

	private static final String EMPLOYEE_FILE = "Employee_Record_";
	private static final String COLUMN_DELIMITER = "@^@";

	private SimpleDateFormat finalFormat = new SimpleDateFormat("dd MMMM yyyy");
	private String fileSuffix = finalFormat.format(new Date()) + ".txt";

	@Autowired
	DataSource dataSource;

	@Value("${common.folder}")
	String folder;

	@Value("${dbViewList}")
	String dbViewList;
	
	@Value("${gridSize}")
	int gridSize;

	// Partitioner --start

	@Bean
	@StepScope
	public RangePartitioner partitioner() {
		return new RangePartitioner();
	}

	// Partitioner -- End

	// Item Readers-- start

	@Bean
	@Qualifier("jdbcRecordReader")
	@StepScope
	public JdbcCursorItemReader<BaseTo> jdbcRecordReader(
			@Value("#{stepExecutionContext[fromId]}") int fromId,
			@Value("#{stepExecutionContext[toId]}") int toId) {
		JdbcCursorItemReader<BaseTo> reader = new JdbcCursorItemReader<BaseTo>();
		reader.setDataSource(dataSource);
		reader.setSql("SELECT * FROM EMPLOYEE WHERE EMPLOYEE_ID >= " + fromId
				+ " AND EMPLOYEE_ID <= " + toId);
		reader.setRowMapper(new BeanPropertyRowMapper<BaseTo>() {
			@Override
			public BaseTo mapRow(ResultSet rs, int rowNumber)
					throws SQLException {
				BaseTo baseTo = new BaseTo();
				// To dynamically add sections to be populated through
				// properties files instead of changing code
				baseTo.setDbViewList(dbViewList);
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
		return reader;
	}

	// Item Readers -end

	// Item Processors-- Start

	@Bean
	public ItemProcessor<BaseTo, BaseTo> etlDataRecordProcessor() {
		return new EtlItemProcessor();
	}

	// Item Processors -end
	// Item Writer-- Start

	@Bean
	@Qualifier("fileWriter")
	@StepScope
	public FlatFileItemWriter<BaseTo> flatFileItemWriter() {
		FlatFileItemWriter<BaseTo> writer = new FlatFileItemWriter<BaseTo>();
		writer.setResource(new FileSystemResource(folder + EMPLOYEE_FILE
				+ fileSuffix));
		writer.setAppendAllowed(true);
		DelimitedLineAggregator<BaseTo> delLineAgg = new DelimitedLineAggregator<BaseTo>();
		delLineAgg.setDelimiter("|");
		BeanWrapperFieldExtractor<BaseTo> fieldExtractor = new BeanWrapperFieldExtractor<BaseTo>();
		fieldExtractor.setNames(new String[] { "text" });
		delLineAgg.setFieldExtractor(fieldExtractor);
		writer.setLineAggregator(delLineAgg);
		return writer;
	}

	// Item Writer-- End

	@Qualifier("step")
	@Bean
	public Step step(StepBuilderFactory stepBuilderFactory,
			@Qualifier("jdbcRecordReader") JdbcCursorItemReader<BaseTo> reader,
			@Qualifier("fileWriter") FlatFileItemWriter<BaseTo> writer,
			ItemProcessor<BaseTo, BaseTo> processor) {
		return stepBuilderFactory.get("step")
				.<BaseTo, BaseTo> chunk(100)
				.reader(reader).processor(processor).writer(writer).stream(writer).build();
	}

	// end::jobstep[]

	@Bean
	public Job etlJob(JobBuilderFactory jobs,
			@Qualifier("partitionStep") Step s) {

		return jobs.get("batchJob").incrementer(new RunIdIncrementer()).flow(s)
				.end().build();
	}

	@Bean
	@Qualifier("partitionStep")
	public Step partitionStep(StepBuilderFactory stepBuilderFactory,
			@Qualifier("step") Step s) {
		return stepBuilderFactory.get("partitionStep")
				.partitioner(s)
				.partitioner("step", partitioner()).gridSize(gridSize)
				.taskExecutor(new SimpleAsyncTaskExecutor()).build();
	}

}

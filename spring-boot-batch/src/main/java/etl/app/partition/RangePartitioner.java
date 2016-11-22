package etl.app.partition;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Value;

public class RangePartitioner implements Partitioner {
	@Value("${fromId}")
	int from;

	@Value("${toId}")
	int to;

	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {

		Map<String, ExecutionContext> result = new HashMap<String, ExecutionContext>();

		int range = to;
		int fromId = from;
		int toId = range;

		for (int i = 1; i <= gridSize; i++) {
			ExecutionContext value = new ExecutionContext();

			value.putInt("fromId", fromId);
			value.putInt("toId", toId);

			// give each thread a name
			value.putString("name", "Thread" + i);

			result.put("partition" + i, value);

			fromId = toId + 1;
			toId += range;

		}

		return result;
	}

}

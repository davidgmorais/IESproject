package ies.project.toSeeOrNot.utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Wei
 * @date 2020/12/3 15:43
 */
@Slf4j
public class JSONUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJSONString(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return "{}";
    }

}
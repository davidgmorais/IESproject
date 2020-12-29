package ies.project.toSeeOrNot.utils.consuming;

import ies.project.toSeeOrNot.ToSeeOrNotApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class DynamicDataScript {
    private static final Logger log = LoggerFactory.getLogger(ToSeeOrNotApplication.class);

    public void run() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String id = "tt";
        for(int i = 45000; i < 9999999;i++) {
            if (i < 10000){
                id = "tt000" + i;
            }else if (i < 100000){
                id = "tt00" + i;
            }else if (i < 1000000){
                id = "tt0" + i;
            }else{
                id = "tt" + i;
            }
            String url = "http://www.omdbapi.com/?apikey=688e4278&i=" + id;
            Movie movie = restTemplate.getForObject(url, Movie.class);
            if(i % 100 == 0){
                log.info(movie.toString());
            }
        }
    }
}

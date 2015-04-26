package utils;

import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.jongo.Mapper;
import org.jongo.marshall.jackson.JacksonMapper;
import uk.co.panaxiom.playjongo.JongoMapperFactory;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * Nothing much to explain, It's registering Joda to be used in Jongo
 */
public class CustomJongoMapperFactory implements JongoMapperFactory {

    @Override
    public Mapper create() {
        return new JacksonMapper.Builder().registerModule(new JodaModule()).build();
    }

}

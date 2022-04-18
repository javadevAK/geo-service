import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.geo.GeoServiceImpl.*;

public class GeoServiceImplTest {

    @ParameterizedTest
    @MethodSource("testLocation")
    public void testByIp(String ip, Location expected) {

        GeoServiceImpl geoService = new GeoServiceImpl();

        Location result = geoService.byIp(ip);

        assertEquals(expected, result);
    }

    private static Stream<Arguments> testLocation() {
        return Stream.of(
                Arguments.of("172.0.42.11", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of(MOSCOW_IP, new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of(LOCALHOST, new Location(null, null, null, 0)),
                Arguments.of(NEW_YORK_IP, new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("96.0.42.11", new Location("New York", Country.USA, null, 0))
        );
    }
}

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.startsWith;

public class MessageSenderMockTest {

    @Test
    void messageSenderTestRu() {
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp("172.0.42.11"))
                .thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.RUSSIA))
                        .thenReturn("Добро пожаловать");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headersActual = new HashMap<String, String>();

        headersActual.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.0.42.11");

        String result = messageSender.send(headersActual);

        Assertions.assertEquals("Добро пожаловать", result);
    }

    @Test
    void messageSenderTestEng() {
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp("96.0.42.11"))
                .thenReturn(new Location("New York", Country.USA, null, 0));

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.USA))
                        .thenReturn("Welcome");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headersActual = new HashMap<String, String>();

        headersActual.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.0.42.11");

        String actualMessage = messageSender.send(headersActual);
        String expectedMessage = "Welcome";

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void messageSenderEngStartWithTest() {
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(startsWith("96.")))
                .thenReturn(new Location("New York", Country.USA, null, 0));

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.USA))
                .thenReturn("Welcome");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headersActual = new HashMap<String, String>();

        headersActual.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.0.72.11");

        String actualMessage = messageSender.send(headersActual);
        String expectedMessage = "Welcome";

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

}

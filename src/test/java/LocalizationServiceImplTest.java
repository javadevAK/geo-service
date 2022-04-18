import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.stream.Stream;


public class LocalizationServiceImplTest {

    @ParameterizedTest
    @MethodSource("source")
    public void localeTest(String message, Country country) {
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();

        Assertions.assertEquals(message, localizationService.locale(country));
    }

    private static Stream<Arguments> source() {
        return Stream.of(
                Arguments.of("Добро пожаловать", Country.RUSSIA),
                Arguments.of("Welcome", Country.USA),
                Arguments.of("Welcome", Country.BRAZIL),
                Arguments.of("Welcome", Country.GERMANY)
        );
    }
}

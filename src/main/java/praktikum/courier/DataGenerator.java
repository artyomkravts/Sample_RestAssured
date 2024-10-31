package praktikum.courier;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import praktikum.courier.request.CreateCourier;
import praktikum.courier.request.CreateOrder;
import praktikum.courier.request.LoginCourier;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class DataGenerator {
    public static LoginCourier getRandomCourierLogPass() {
        String testLogin = "TestGuy" + RandomStringUtils.randomAlphanumeric(15);
        String testPassword = RandomStringUtils.randomNumeric(5);

        return new LoginCourier(testLogin, testPassword);
    }

    public static CreateCourier getRandomCourier() {
        String testLogin = "TestGuy" + RandomStringUtils.randomAlphanumeric(15);
        String testPassword = RandomStringUtils.randomNumeric(5);
        String testFirstName = RandomStringUtils.randomAlphabetic(10);

        return new CreateCourier(testLogin, testPassword, testFirstName);
    }

    public static CreateOrder getRandomOrder() {
        String firstName = RandomStringUtils.randomAlphabetic(10);
        String lastName = RandomStringUtils.randomAlphabetic(10);
        String address = RandomStringUtils.randomAlphanumeric(20);
        String metroStation = getRandomMetro(); // Вообще согласно доке должен быть String, но так тоже работает
        String phone = "+7" + RandomStringUtils.randomNumeric(10);
        Integer rentTime = Integer.parseInt(RandomStringUtils.randomNumeric(1));
        String deliveryDate = getFutureDate();
        String comment = RandomStringUtils.randomAlphabetic(10);

        return new CreateOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment);
    }

    public static CreateOrder getRandomOrderWithFirstName(String firstNameInput) {
        String firstName = firstNameInput;
        String lastName = RandomStringUtils.randomAlphabetic(10);
        String address = RandomStringUtils.randomAlphanumeric(20);
        String metroStation = getRandomMetro(); // Вообще согласно доке должен быть String, но так тоже работает
        String phone = "+7" + RandomStringUtils.randomNumeric(10);
        Integer rentTime = Integer.parseInt(RandomStringUtils.randomNumeric(1));
        String deliveryDate = getFutureDate();
        String comment = RandomStringUtils.randomAlphabetic(10);

        return new CreateOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment);
    }

    private static String getFutureDate() {
        LocalDate currentDate = LocalDate.now();

        LocalDate tomorrowDate = currentDate.plusDays(RandomUtils.nextInt(1, 6));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String formattedDate = tomorrowDate.format(formatter);

        return formattedDate;
    }

    private static String getRandomMetro() {
        Random random = new Random();

        int randomNumber = random.nextInt(237) + 1;

        String randomMetro = String.valueOf(randomNumber);

        return randomMetro;
    }
}

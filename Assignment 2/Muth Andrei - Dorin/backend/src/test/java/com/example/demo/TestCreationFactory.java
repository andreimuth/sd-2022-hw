package com.example.demo;

import com.example.demo.book.dto.BookDto;
import com.example.demo.book.model.Book;
import com.example.demo.user.dto.UserListDto;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class TestCreationFactory {

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls) {
        return listOf(cls, (Object[]) null);
    }

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls, Object... parameters) {
        int nrElements = new Random().nextInt(10) + 5;
        Supplier<?> supplier;

        if (cls.equals(UserListDto.class)) {
            supplier = TestCreationFactory::newUserListDto;
        } else if (cls.equals(Book.class)) {
            supplier = TestCreationFactory::newBook;
        } else if (cls.equals(BookDto.class)) {
            supplier = TestCreationFactory::newBookDto;
        } else {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                        (T) finalSupplier.get()
                ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }

    private static UserListDto newUserListDto() {
        return UserListDto.builder()
                .id(randomLong())
                .name(randomString())
                .email(randomEmail())
                .build();
    }

    private static Book newBook() {
        return Book.builder()
                .id(randomLong())
                .title(randomString())
                .genre(randomString())
                .price(randomFloat())
                .quantity(randomLong())
                .build();
    }

    private static BookDto newBookDto() {
        return BookDto.builder()
                .id(randomLong())
                .title(randomString())
                .genre(randomString())
                .price(randomFloat())
                .quantity(randomLong())
                .build();
    }

    public static float randomFloat() {
        return new Random().nextFloat();
    }

    public static String randomEmail() {
        return randomString() + "@" + randomString() + ".com";
    }

    public static byte[] randomBytes() {
        byte[] bytes = new byte[Math.toIntExact(randomLong())];
        new Random().nextBytes(bytes);
        return bytes;
    }

    public static long randomLong() {
        return new Random().nextInt(1999);
    }

    public static Boolean randomBoolean() {
        return new Random().nextBoolean();
    }

    public static int randomBoundedInt(int upperBound) {
        return new Random().nextInt(upperBound);
    }

    public static String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}

import org.junit.jupiter.api.*;

public class JUnitTest {

    @Test
    public void junitTest1() {
        int a = 1;
        int b = 2;
        int sum = 3;

        int result = a + b;

        Assertions.assertEquals(sum,result);
    }


    @DisplayName("1+3 = 3")
    @Test
    public void junitTest2() {
        int a = 1;
        int b = 3;

        int res = 1+3;

        Assertions.assertEquals(3, res);

    }

    @BeforeEach
    public void prepare() {
        System.out.println("ready");
    }

    @AfterEach
    public void cleanup() {
        System.out.println("clean");
    }

    @BeforeAll
    public static void prepareAll() {
        System.out.println("init ready");
    }

    @AfterAll
    public static void cleanupAll() {
        System.out.println("final clean");
    }
}

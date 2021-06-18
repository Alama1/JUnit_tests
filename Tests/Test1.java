import org.junit.*;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class Test1 {

    private UsersService usersService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void globalSetUp() {
        System.out.println("Код запустився коректно");
    }

    @Before
    public void setUp() {
        Users user1 = new Users("John", LocalDate.of(1994, 3, 17));
        Users user2 = new Users("Alice", LocalDate.of(1970, 4, 17));
        Users user3 = new Users("Melinda", LocalDate.of(1997, 6, 23));
        List<Users> usersList = new ArrayList<>();
        usersList.add(user1);
        usersList.add(user2);
        usersList.add(user3);
        usersService = new UsersService(usersList);
    }

    @Test
    public void whenCreateNewUserThenReturnListWithNewUser() throws Exception {
        assertThat(usersService.getUsers().size(), is(3));
        usersService.createNewUser("New User", LocalDate.of(1990, 2, 1));
        assertThat(usersService.getUsers().size(), is(4));
    }

    @Test
    public void whenRemoveUserWhenRemoveUserByName(){
        usersService.removeUser("Melinda");
        List<Users> usersList = usersService.getUsers();
        assertThat(usersList.size(), is(2));
    }

    @Test
    public void whenCreateNewUserWithoutNameThenThrowCustomFieldException() throws Exception {
        thrown.expect(CustomFieldException.class);
        thrown.expectMessage("Имя не може бути null!");
        usersService.createNewUser(null, LocalDate.of(1990, 2, 1));
    }

    @Test
    public void whenCreateNewUserWithoutDateOfBirthThenThrowCustomFieldException() throws Exception {
        thrown.expect(CustomFieldException.class);
        thrown.expectMessage("Дата народження не може бути null!");
        usersService.createNewUser("Dave", null);
    }


    @Test
    public void whenIsBirthDayWhenNotBirthdayThenReturnFalse() throws CustomFieldException {
        boolean isBirthday = usersService.isBirthDay(usersService.getUsers().get(0), LocalDate.of(1990, 3, 17));
        assertTrue(isBirthday);
    }

    @AfterClass
    public static void tearDown() {
        System.out.println("Тести закончились");
    }

    @After
    public void afterMethod() {
        System.out.println("Всi тести пройденi");
    }
}
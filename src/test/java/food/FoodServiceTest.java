package food;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

@RunWith(MockitoJUnitRunner.class)
public class FoodServiceTest {
    FoodService foodService;
    @Mock
    FoodRepository foodRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private String foodType = "pizza";
    private MockMultipartFile pic = new MockMultipartFile("foo", new byte[0]);
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        foodService = new FoodServiceImpl(foodRepository);
    }
    /* @Test
    public void listMenu_menusFound() {
        String testFoodType = "pizza";
        Food testFood = new Food();
        testFood.setFoodType(testFoodType);
        List<Food> listFood = new ArrayList<>();
        listFood.add(testFood);
        when(foodRepository.findAllByFoodType(testFoodType)).thenReturn(listFood);
        FoodDTO actualFood = foodService.filterFoodByType(testFoodType);
        assertEquals(listFood, actualFood.getFoods());
    } */
    /* @Test
    public void findById_apprenticeFound() {
        long testId = 23;
        Apprentice testApprentice = new Apprentice();
        testApprentice.setId(testId);
        when(apprenticeRepository.findById(testId)).thenReturn(testApprentice);
        Apprentice actualApprentice = apprenticeService.findById(testId);
        assertEquals(testApprentice, actualApprentice);
    }
    @Test
    public void filterApprentices_success() {
        ListApprenticesSQLParams listApprenticesSQLParams = new ListApprenticesSQLParams("Niteesh Baranwal", new String[]{"Budapest"}, languages,(long) languages.length, (long)preferences.length, date, preferences,  new String[]{"Alopex"}, technologies, (long)technologies.length, null, new long[]{1});
        User currentUser = getUserWithValidFields();
        List<Language> languagesKnown = new ArrayList();
        languagesKnown.add(new Language("English"));
        languagesKnown.add(new Language("Hungarian"));
        List<Technology> technologiesKnown = new ArrayList();
        List<Preference> positionsKnown = new ArrayList();
        technologiesKnown.add(new Technology("Java"));
        Apprentice apprentice = new Apprentice(firstName, lastName, date, cohort);
        List<Apprentice> listApprentice = new ArrayList<>();
        listApprentice.add(apprentice);
        when(apprenticeRepository.findApprenticesByQueryParams(listApprenticesSQLParams)).thenReturn(listApprentice);
        List<Apprentice> outputList = apprenticeService.filterApprentices(listApprenticesSQLParams, currentUser);
        assertNotNull(outputList);
        assertEquals(listApprentice.size(), outputList.size());
        assertEquals(listApprentice, outputList);
        assertEquals(listApprentice.stream().findFirst().get(), outputList.stream().findFirst().get());
    } */
 /*
    private User getUserWithValidFields() {
        User user = new User();
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setCompanyName("companyName");
        user.setEmail("test@test.com");
        user.setPassword("password");

        return user;
    } */
}

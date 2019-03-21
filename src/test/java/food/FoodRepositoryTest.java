package food;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import threee.food.Food;
import threee.food.FoodRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)

@DataJpaTest

public class FoodRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FoodRepository foodRepository;

    @Test
    public void whenFindByName_thenReturnEmployee() {
        // given
        Food alex = new Food(false, "pizza", "sonkás", "só", "vékony,",
                1, 1000, 1, "van");

        foodRepository.save(alex);
        entityManager.persist(alex);
        entityManager.flush();

        // when
        List<Food> found = foodRepository.findAllByFoodType(alex.getFoodType());

        // then

        assertThat(found).hasSize(1);

        assertThat(found.get(0).getFoodType())
                .isEqualTo(alex.getFoodType());
    }
}

/*
package com.threee;

import Food;
import FoodRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)

@DataJpaTest

public class Test1 {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FoodRepository foodRepository;

    @Test
    public void whenFindByName_thenReturnEmployee() {
        // given
        Food alex = new Food(false, "pizza", "sonkás", "só", "vékony,",
                1, "van", false, "van");

        foodRepository.save(alex);
        entityManager.persist(alex);
        entityManager.flush();

        // when
        List<Food> found = foodRepository.findAllByFoodType(alex.getFoodType());

        // then

        assertThat(found).hasSize(1);

        assertThat(found.get(0).getFoodType())
                .isEqualTo(alex.getFoodType());
    }
}
*/

/*
@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private FoodRepository foodRepository;

    @Test
    void findsByfoodType() {
        Food customer =  new Food(false, "pizza", "sonkás", "só", "vékony,",
                1, "van", false, "van");

        foodRepository.save(customer);

        List<Food> foundCustomers = foodRepository.findAllByFoodType("pizza");
        assertThat(foundCustomers).hasSize(1);
    }

}
*/
    /*
    @Test
    public void whenFindByName_thenReturnEmployee() {
        // given
        Food alex = new Food(false, "pizza", "sonkás", "só", "vékony,",
                1, "van", false, "van");
        entityManager.persist(alex);
        entityManager.flush();

        // when
        List<Food> found = foodRepository.findAllByFoodType(alex.getFoodType());

        // then
        assertThat(found).hasSize(1);
        String found1 = found.get(2)
                found1.isEqualTo(alex.getFoodType());
    }

    private WebTestClient.ListBodySpec<Object> assertThat(List<Food> found) {
        return null;
    } */
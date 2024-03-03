package sample.cafekiosk.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverage.Americano;

import static org.assertj.core.api.Assertions.*;

class CafeKioskTest {

    @Test
    void add_basic_test(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());
        System.out.println("음료 수: " + cafeKiosk.getBeverages().size());
    }

    @Test
    void add(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());
        assertThat(cafeKiosk.getBeverages().size()).isEqualTo(1);
        assertThat(cafeKiosk.getBeverages()).hasSize(1);
        assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    void remove(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);

        assertThat(cafeKiosk.getBeverages()).hasSize(1);

        cafeKiosk.remove(americano);
        assertThat(cafeKiosk.getBeverages()).isEmpty();

    }
}
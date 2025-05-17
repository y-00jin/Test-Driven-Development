package chap03;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * # 유료 서비스
 * ## 조건
 *  - 매달 1만원을 선불로 납부한다. 남부일 기준으로 한달 뒤가 서비스 만료일이 된다
 *  - 2개월 이상 요금을 납부할 수 있다
 *  - 10만원을 납부하면 서비스를 1년 제공한다.
 */
public class ExpiryDateCalculatorTest {

    @Test
    void 만원_납부하면_한달_뒤_만료일() {
        assertExpiryDate(

                PayData.builder()
                        .billingDate(LocalDate.of(2025, 3, 1))
                        .payAmount(10_000)
                        .build()
                , LocalDate.of(2025, 4, 1)

        );
    }

    @Test
    void 납부일과_한달_뒤_일자가_같지_않음() {
        assertExpiryDate(

                PayData.builder()
                        .billingDate(LocalDate.of(2025, 1, 31))
                        .payAmount(10_000)
                        .build()
                , LocalDate.of(2025, 2, 28)

        );
    }


    @Test
    void 첫_납부일과_만료일_일자가_다를떄_만원_납부() {

        PayData payData = PayData.builder()
                .firstBillingDate(LocalDate.of(2025, 1, 31))
                .billingDate(LocalDate.of(2025, 2, 28))
                .payAmount(10_000)
                .build();
        assertExpiryDate(payData, LocalDate.of(2025, 3, 31));

        PayData payData2 = PayData.builder()
                .firstBillingDate(LocalDate.of(2025, 1, 30))
                .billingDate(LocalDate.of(2025, 2, 28))
                .payAmount(10_000)
                .build();
        assertExpiryDate(payData2, LocalDate.of(2025, 3, 30));
    }


    @Test
    void 이만원_이상_납부하면_비례해서_만료일_계산() {
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019, 3, 1))
                        .payAmount(20_000)
                        .build(),
                LocalDate.of(2019, 5, 1));
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019, 3, 1))
                        .payAmount(30_000)
                        .build(),
                LocalDate.of(2019, 6, 1));
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019, 1, 31))
                        .payAmount(30_000)
                        .build(),
                LocalDate.of(2019, 4, 30));
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2018, 12, 31))
                        .payAmount(20_000)
                        .build(),
                LocalDate.of(2019, 2, 28));
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019, 1, 31))
                        .payAmount(50_000)
                        .build(),
                LocalDate.of(2019, 6, 30));
    }

    @Test
    void 첫_납부일과_만료일_일자가_다를때_이만원_이상_납부() {
        assertExpiryDate(
                PayData.builder()
                        .firstBillingDate(LocalDate.of(2019, 1, 31))
                        .billingDate(LocalDate.of(2019, 2, 28))
                        .payAmount(20_000)
                        .build(),
                LocalDate.of(2019, 4, 30));
        assertExpiryDate(
                PayData.builder()
                        .firstBillingDate(LocalDate.of(2019, 1, 31))
                        .billingDate(LocalDate.of(2019, 2, 28))
                        .payAmount(40_000)
                        .build(),
                LocalDate.of(2019, 6, 30));
        assertExpiryDate(
                PayData.builder()
                        .firstBillingDate(LocalDate.of(2019, 3, 31))
                        .billingDate(LocalDate.of(2019, 4, 30))
                        .payAmount(30_000)
                        .build(),
                LocalDate.of(2019, 7, 31));
        assertExpiryDate(
                PayData.builder()
                        .firstBillingDate(LocalDate.of(2019, 10, 31))
                        .billingDate(LocalDate.of(2019, 11, 30))
                        .payAmount(30_000)
                        .build(),
                LocalDate.of(2020, 2, 29));
    }


    @Test
    void 십만원을_납부하면_1년_제공() {
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019, 1, 28))
                        .payAmount(100_000)
                        .build(),
                LocalDate.of(2020, 1, 28));
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2020, 2, 29))
                        .payAmount(100_000)
                        .build(),
                LocalDate.of(2021, 2, 28));
    }

    private void assertExpiryDate(PayData payData, LocalDate expectedExpiryDate) {
        ExpiryDateCalculator cal = new ExpiryDateCalculator();
        LocalDate realExpiryDate = cal.calculateExpiryDate(payData);
        assertEquals(expectedExpiryDate, realExpiryDate);
    }


}

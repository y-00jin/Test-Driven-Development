package chap03;

import java.time.LocalDate;
import java.time.YearMonth;

public class ExpiryDateCalculator {

    public LocalDate calculateExpiryDate(PayData payData) {
        int addedMonths = payData.getPayAmount() == 100_000 ? 12 : payData.getPayAmount() / 10_000; // 10원 납부 시 1년 제공
        if (payData.getFirstBillingDate() != null) {
            return expiryDateUsingFirstBillingDate(payData, addedMonths);
        } else {
            return payData.getBillingDate().plusMonths(addedMonths);
        }
    }

    // 첫 납부일 존재하는 경우 만료일 계산 함수
    private LocalDate expiryDateUsingFirstBillingDate(PayData payData, int addedMonths) {
        LocalDate candidateExp = payData.getBillingDate().plusMonths(addedMonths);      // 후보 만료일 계산
        final int dayOfFirstBilling = payData.getFirstBillingDate().getDayOfMonth();    // 첫번쨰 납부일의 일자 추출
        if (dayOfFirstBilling != candidateExp.getDayOfMonth()) {    // 만료일의 일자와 첫번째 납부한 일자가 같지 않은 경우 일자 변경
            final int dayLenOfCandiMon = YearMonth.from(candidateExp).lengthOfMonth();  // 후보 만료일의 마지막 날 조회
            if (dayLenOfCandiMon < dayOfFirstBilling) {  // 후보 만료일 마지막 날이 첫번째 납부일의 날보다 작은 경우
                return candidateExp.withDayOfMonth(dayLenOfCandiMon);   // 후보 만료일의 마지말 날로 변경
            }
            return candidateExp.withDayOfMonth(dayOfFirstBilling);      // 그렇지 않은 경우 첫번째 납부일의 일자로 변경
        } else {
            return candidateExp;
        }
    }
}

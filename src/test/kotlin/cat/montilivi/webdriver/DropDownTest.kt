package cat.montilivi.webdriver

import org.junit.jupiter.api.Assertions.assertTrue
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import kotlin.test.Test

class DropdownTest : TestBasic() {
    @Test
    @Throws(InterruptedException::class)
    fun testApp() {
        driver
            .navigate().to("https://the-internet.herokuapp.com/dropdown")
        val options: MutableList<WebElement?> =
            driver.findElements(By.xpath(".//*[@id='dropdown']/option"))

        for (i in 1..<options.size) {
            val option: WebElement =
                driver.findElement(
                    By.xpath(".//*[@id='dropdown']/option[" + (i + 1) + "]")
                )
            option.click()
            assertTrue(option.isSelected())
        }
    }
}
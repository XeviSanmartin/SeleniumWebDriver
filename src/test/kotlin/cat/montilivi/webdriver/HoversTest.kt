package cat.montilivi.webdriver

import org.junit.jupiter.api.Assertions.assertTrue
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import kotlin.test.Test

class HoversTest : TestBasic() {
    @Test
    @Throws(InterruptedException::class)
    fun hoversTest() {
        driver.get("https://the-internet.herokuapp.com/hovers")
        Thread.sleep(3000)
        // Find and hover over desired element
        val avatar: WebElement? = driver.findElement(By.className("figure"))
        val builder = Actions(driver)
        builder.moveToElement(avatar).build().perform()
        Thread.sleep(3000)
        // Wait until the hover appears
        val wait = WebDriverWait(driver, Duration.ofSeconds(5))
        wait.until<WebElement?>(ExpectedConditions.visibilityOfElementLocated(By.className("figcaption")))
        Thread.sleep(3000)
        // Assert that the hover displayed
        assertTrue(driver.findElement(By.className("figcaption")).isDisplayed())
        Thread.sleep(3000)
    }
}
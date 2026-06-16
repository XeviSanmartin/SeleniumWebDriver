package cat.montilivi.webdriver

import junit.framework.Assert
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import kotlin.test.Test

class HoversImprovedTest : TestBasic() {
    @Test
    @Throws(InterruptedException::class)
    fun hoversTest() {
        driver.get("http://the-internet.herokuapp.com/hovers")
        // Find and hover over desired element
        val avatars: MutableList<WebElement?> = driver.findElements(By.xpath("//*[@id=\'content\']/div/div"))

        for (i in 1..avatars.size) {
            val avatar: WebElement? = driver.findElement(By.xpath("//*[@id=\'content\']/div/div[" + i + "]/img"))
            val builder = Actions(driver)
            builder.moveToElement(avatar).build().perform()
            // Wait until the hover appears
            val wait = WebDriverWait(driver, Duration.ofSeconds(5))
            wait.until<WebElement?>(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\'content\']/div/div[" + i + "]/div/h5")))
            // Assert that the hover displayed
            Assert.assertTrue(
                driver.findElement(By.xpath("//*[@id=\'content\']/div/div[" + i + "]/div/h5")).isDisplayed()
            )
            Assert.assertTrue(
                driver.findElement(By.xpath("//*[@id=\'content\']/div/div[" + i + "]/div/h5")).getText()
                    .contains("name: user" + i)
            )
        }
    }
}
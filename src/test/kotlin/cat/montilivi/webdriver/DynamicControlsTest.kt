package cat.montilivi.webdriver

import junit.framework.Assert
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import java.time.Duration
import kotlin.test.Test

class DynamicControlsTest : TestBasic() {
    @Test
    @Throws(InterruptedException::class)
    fun titleTest() {
        driver.navigate().to("http://the-internet.herokuapp.com/dynamic_controls")
        val checkbox: WebElement = driver.findElement(By.id("checkbox"))
        Assert.assertTrue(checkbox.isDisplayed())
        checkbox.click()
        val removeBtn: WebElement = driver.findElement(By.xpath("//*[@id='checkbox-example']/button"))
        removeBtn.click()
        val wait = WebDriverWait(driver, Duration.ofSeconds(20))
        wait.until<Boolean?>(
            ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(By.id("loading")),
                ExpectedConditions.presenceOfElementLocated(By.id("message"))
            )
        )
        var message: WebElement = driver.findElement(By.id("message"))
        Assert.assertTrue(message.isDisplayed())

        val enableBtn: WebElement = driver.findElement(By.xpath("//*[@id='input-example']/button"))
        enableBtn.click()
        wait.until<Boolean?>(
            ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(By.id("loading")),
                ExpectedConditions.presenceOfElementLocated(By.id("message"))
            )
        )
        message = driver.findElement(By.id("message"))
        Assert.assertTrue(message.isDisplayed())
    }
}
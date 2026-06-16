package cat.montilivi.webdriver

import org.openqa.selenium.Alert
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.Duration

class AlertsTest : TestBasic() {
    fun acceptAlert() {
        try {
            val wait = WebDriverWait(driver, Duration.ofSeconds(2))
            wait.until<Alert?>(ExpectedConditions.alertIsPresent())
            val alert: Alert = driver.switchTo().alert()
            alert.accept()
        } catch (e: Exception) {
            //exception handling
        }
    }

    fun dismissAlert() {
        try {
            val wait = WebDriverWait(driver, Duration.ofSeconds(2))
            wait.until<Alert?>(ExpectedConditions.alertIsPresent())
            val alert: Alert = driver.switchTo().alert()
            alert.dismiss()
        } catch (e: Exception) {
            //exception handling
        }
    }

    fun promptAlert() {
        try {
            val wait = WebDriverWait(driver, Duration.ofSeconds(2))
            wait.until<Alert?>(ExpectedConditions.alertIsPresent())
            val alert: Alert = driver.switchTo().alert()
            alert.sendKeys("Hola hola super hola")
            alert.accept()
        } catch (e: Exception) {
            //exception handling
        }
    }


    @Test
    @Throws(InterruptedException::class)
    fun testApp() {
        driver.navigate().to("https://the-internet.herokuapp.com/javascript_alerts")
        val resultBanner: WebElement = driver.findElement(By.id("result"))
        driver.findElement(By.xpath(".//*[@id='content']/div/ul/li[1]/button")).click()
        Thread.sleep(2000)
        acceptAlert()
        Thread.sleep(2000)
        Assertions.assertEquals("You successfully clicked an alert", resultBanner.getText())
        driver.findElement(By.xpath(".//*[@id='content']/div/ul/li[2]/button")).click()
        Thread.sleep(2000)
        acceptAlert()
        Thread.sleep(2000)
        Assertions.assertEquals("You clicked: Ok", resultBanner.getText())
        driver.findElement(By.xpath(".//*[@id='content']/div/ul/li[2]/button")).click()
        Thread.sleep(2000)
        dismissAlert()
        Thread.sleep(2000)
        Assertions.assertEquals("You clicked: Cancel", resultBanner.getText())
        driver.findElement(By.xpath(".//*[@id='content']/div/ul/li[3]/button")).click()
        Thread.sleep(2000)
        promptAlert()
        Thread.sleep(2000)
        Assertions.assertEquals("You entered: Hola hola super hola", resultBanner.getText())
    }
}

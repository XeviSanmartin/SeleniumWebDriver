package cat.montilivi.webdriver

import org.junit.jupiter.api.Assertions.assertTrue
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.FluentWait
import org.openqa.selenium.support.ui.Wait
import java.io.File
import java.time.Duration
import kotlin.test.Test

class UploadTest : TestBasic() {
    @Test
    @Throws(InterruptedException::class)
    fun testApp() {
        // Controla el ritme per a Safari
        val wait: Wait<WebDriver?> = FluentWait<WebDriver?>(driver).withTimeout(Duration.ofSeconds(15)).pollingEvery(
            Duration.ofSeconds(1)
        ).ignoring(NoSuchElementException::class.java)

        driver.navigate().to("https://the-internet.herokuapp.com/upload")
        val fileUpload: WebElement = driver.findElement(By.id("file-upload"))
        val file = File(
            ("src" + File.separator + "test"
                    + File.separator + "resources" + File.separator + "2-logo-B_activa.png")
        )
        fileUpload.sendKeys(file.getAbsolutePath())
        val buttonUpload: WebElement = driver.findElement(By.id("file-submit"))
        buttonUpload.click()

        // Espera explícita para Safari
        if (System.getProperty("browser").equals("safari", ignoreCase = true)) {
            Thread.sleep(2000) // Espera para Safari
        }

        val uploadedFiles: WebElement = driver.findElement(By.id("uploaded-files"))
        wait.until<WebElement?>(ExpectedConditions.visibilityOfElementLocated(By.id("uploaded-files")))
        assertTrue(uploadedFiles.isDisplayed())
    }
}
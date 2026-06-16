package cat.montilivi.webdriver

import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.junit.jupiter.api.Test
import java.time.Duration
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class MultipleWindowsTest : TestBasic() {
    @Test
    @Throws(InterruptedException::class)
    fun multipleWindows() {
        driver.get("http://the-internet.herokuapp.com/windows")

        val originalWindow: String? = driver.getWindowHandle()

        driver.findElement(By.xpath("//a[@href=\'/windows/new\']")).click()

        val wait = WebDriverWait(driver, Duration.ofSeconds(20))
        Thread.sleep(3000) // Temas del Firefox
        wait.until<Boolean?>(ExpectedConditions.numberOfWindowsToBe(2))

        // numberOfWindowsToBe de Firefox no se interpreta bien, por eso el sleep
        var newWindow = ""
        for (handle in driver.getWindowHandles()) {
            println(handle)
            if (handle != originalWindow) {
                newWindow = handle
            }
        }

        // Verificar ventana original
        driver.switchTo().window(originalWindow)

        assertNotEquals("New Window", driver.title)

        // Verificar nueva ventana
        driver.switchTo().window(newWindow)
        assertEquals("New Window", driver.title)

        // ✅ Cerrar la ventana nueva y volver a la original
        driver.close()
        driver.switchTo().window(originalWindow)
    }

    @Test
    @Throws(InterruptedException::class)
    fun multipleWindowsRedux() {
        driver.get("http://the-internet.herokuapp.com/windows")

        val firstWindow: String? = driver.getWindowHandle()
        var newWindow = ""

        driver.findElement(By.cssSelector(".example a")).click()

        val wait = WebDriverWait(driver, Duration.ofSeconds(20))
        Thread.sleep(3000) // Temas de tiempos del Firefox
        wait.until<Boolean?>(ExpectedConditions.numberOfWindowsToBe(2))

        // numberOfWindowsToBe de Firefox no se interpreta bien, por eso el sleep
        val allWindows: MutableSet<String> = driver.getWindowHandles()

        for (window in allWindows) {
            println(window)
            if (window != firstWindow) {
                newWindow = window
            }
        }

        driver.switchTo().window(firstWindow)
        assertNotEquals("New Window", driver.title)

        driver.switchTo().window(newWindow)
        assertEquals("New Window", driver.title)

        // ✅ Cerrar la ventana nueva y volver a la original
        driver.close()
        driver.switchTo().window(firstWindow)
    }
}
package cat.montilivi.webdriver

import org.junit.jupiter.api.Assertions.assertTrue
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import kotlin.test.Test
import kotlin.test.assertNull

class CheckBoxesTest : TestBasic() {
    @Test
    @Throws(InterruptedException::class)
    fun testApp() {
        //1 Navegar hasta la página
        driver.navigate().to("https://the-internet.herokuapp.com/checkboxes")
        //2 Mirar que el checkbox2 está marcado
        val checkbox2: WebElement =
            driver.findElement(By.xpath(".//*[@id='checkboxes']/input[2]"))
        assertTrue(checkbox2.getAttribute("checked") == "true")
        //2.1 Mirar el checkbox1 que NO está marcado
        val checkbox1: WebElement =
            driver.findElement(By.xpath(".//*[@id='checkboxes']/input[1]"))
        assertNull(checkbox1.getAttribute("checked"))
        //3 Marcar checkbox1
        checkbox1.click()
        //4 Mirar que ha quedado marcado
        assertTrue(checkbox1.getAttribute("checked") == "true")
    }
}
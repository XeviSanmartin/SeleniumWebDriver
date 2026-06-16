package cat.montilivi.webdriver

import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.junit.jupiter.api.Assertions

class DynamicContentTest : TestBasic() {
    @Test
    @Throws(InterruptedException::class)
    fun testApp() {
        driver.navigate().to("https://the-internet.herokuapp.com/dynamic_content")
        var imatge1: WebElement =
            driver.findElement(
                By.xpath(".//*[@id='content']/div[3]/div[1]/img")
            )
        var text1: WebElement =
            driver.findElement(
                By.xpath(".//*[@id='content']/div[3]/div[2]")
            )
        val urlImatge1 = imatge1.getAttribute("src")
        val texttotal1 = text1.getText()
        driver.navigate().refresh() //Regenero el DOM completo
        imatge1 =
            driver.findElement(
                By.xpath(".//*[@id='content']/div[3]/div[1]/img")
            )
        text1 =
            driver.findElement(
                By.xpath(".//*[@id='content']/div[3]/div[2]")
            )
        val urlImatge2 = imatge1.getAttribute("src")
        val texttotal2 = text1.getText()
        Assertions.assertFalse(urlImatge1 == urlImatge2)
        Assertions.assertFalse(texttotal1 == texttotal2)
    }
}
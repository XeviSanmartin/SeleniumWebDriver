package cat.montilivi.webdriver

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import kotlin.test.Test
import kotlin.test.assertTrue

class KeyboardKeysTest : TestBasic() {
    @Test
    @Throws(InterruptedException::class)
    fun KeyboardKeysExample() {
        driver.get("http://the-internet.herokuapp.com/key_presses")
        val builder = Actions(driver)
        builder.sendKeys(Keys.SPACE).build().perform()
        assertTrue(driver.findElement(By.id("result")).getText().equals("You entered: SPACE", ignoreCase = true))
        builder.sendKeys(Keys.LEFT).build().perform()
        assertTrue(driver.findElement(By.id("result")).getText().equals("You entered: LEFT", ignoreCase = true))

        val textBox: WebElement = driver.findElement(By.id("target"))
        textBox.click()
        textBox.sendKeys("Hello World")
        textBox.sendKeys(Keys.ESCAPE)
        assertTrue(driver.findElement(By.id("result")).getText().equals("You entered: ESCAPE", ignoreCase = true))
    }
}
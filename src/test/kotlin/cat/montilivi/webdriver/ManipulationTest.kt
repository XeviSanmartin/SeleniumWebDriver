package cat.montilivi.webdriver

import org.junit.jupiter.api.Assertions.assertTrue
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import kotlin.test.Test

class ManipulationTest : TestBasic() {
    @Test //Opciones de Navegación
    @Throws(Exception::class)
    fun testNavigation() {
        driver.get("https://www.duckduckgo.com/") //Navegar hasta duckduckgo
        driver.findElement(By.id("searchbox_input")).clear() //Limpiar el text box
        driver.findElement(By.id("searchbox_input")).sendKeys("pizza hawaiana") //Escribir en el text box
        driver.findElement(By.id("searchbox_input")).submit() //Submit, darle al enter.
        Thread.sleep(2000) //Espera forzada
        var resultsList: WebElement =
            driver.findElement(By.className("react-results--main")) //Comprobar que el resultado se muestra
        assertTrue(resultsList.isDisplayed())


        driver.get("https://www.duckduckgo.com/") //Navegar hasta duckduckgo
        driver.findElement(By.id("searchbox_input")).clear() //Limpiar el text box
        driver.findElement(By.id("searchbox_input")).sendKeys("tortellini al pesto")
        Thread.sleep(2000) //Espera forzada
        val builder = Actions(driver)
        val searchButton: WebElement? =
            driver.findElement(By.xpath(".//button[@data-mode=\'search\']")) //Click en el text box, no hace nada pero es para mostrar la diferencia entre submit y click.
        builder.moveToElement(searchButton).click().build()
            .perform() //Click en el botón de búsqueda, esto es diferente a submit, ya que submit solo funciona con formularios, mientras que click funciona con cualquier elemento.
        Thread.sleep(2000) //Espera forzada
        resultsList = driver.findElement(By.className("react-results--main")) //Comprobar que el resultado se muestra
        assertTrue(resultsList.isDisplayed())
    }
}
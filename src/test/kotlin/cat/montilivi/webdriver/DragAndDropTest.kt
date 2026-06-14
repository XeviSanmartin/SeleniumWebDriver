package cat.montilivi.webdriver

import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import kotlin.test.Test
import kotlin.test.assertEquals

class DragAndDropTest : TestBasic() {
    @Test
    @Throws(InterruptedException::class)
    fun testApp() {
        driver.navigate().to("https://the-internet.herokuapp.com/drag_and_drop")

        Thread.sleep(2000)

        // Localizar los dos elementos con sus IDs reales
        val columnA: WebElement = driver.findElement(By.id("column-a"))
        val columnB: WebElement = driver.findElement(By.id("column-b"))

        // Verificar estado inicial: A está a la izquierda, B a la derecha
        assertEquals(columnA.findElement(By.tagName("header")).getText(), "A")
        assertEquals(columnB.findElement(By.tagName("header")).getText(), "B")

        // Arrastrar A sobre B (intercambiar posiciones)
        val dragAndDrop = Actions(driver)
        dragAndDrop.dragAndDrop(columnA, columnB).perform()

        Thread.sleep(2000)

        // Verificar que han intercambiado: ahora A está donde estaba B y viceversa
        assertEquals(columnA.findElement(By.tagName("header")).getText(), "B")
        assertEquals(columnB.findElement(By.tagName("header")).getText(), "A")
    }
}
package cat.montilivi.webdriver;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DragAndDropJavascriptTest extends TestBasic {
    @Test
    public void testAppConJS() throws InterruptedException {
        driver.navigate().to("https://the-internet.herokuapp.com/drag_and_drop");
        Thread.sleep(2000);

        WebElement columnA = driver.findElement(By.id("column-a"));
        WebElement columnB = driver.findElement(By.id("column-b"));

        // Script JS que simula los eventos HTML5 drag & drop correctamente
        String script =
                "function simulateDragDrop(sourceNode, destinationNode) {" +
                        "  var EVENT_TYPES = ['dragstart','dragenter','dragover','drop','dragend'];" +
                        "  function createEvent(eventType) {" +
                        "    var event = document.createEvent('CustomEvent');" +
                        "    event.initCustomEvent(eventType, true, true, null);" +
                        "    event.dataTransfer = { data: {}, setData: function(type, val) { this.data[type]=val; }, getData: function(type) { return this.data[type]; } };" +
                        "    return event;" +
                        "  }" +
                        "  var dragEvent = createEvent('dragstart');" +
                        "  sourceNode.dispatchEvent(dragEvent);" +
                        "  destinationNode.dispatchEvent(createEvent('dragenter'));" +
                        "  destinationNode.dispatchEvent(createEvent('dragover'));" +
                        "  var dropEvent = createEvent('drop');" +
                        "  dropEvent.dataTransfer = dragEvent.dataTransfer;" +
                        "  destinationNode.dispatchEvent(dropEvent);" +
                        "  sourceNode.dispatchEvent(createEvent('dragend'));" +
                        "}" +
                        "simulateDragDrop(arguments[0], arguments[1]);";

        ((JavascriptExecutor) driver).executeScript(script, columnA, columnB);
        Thread.sleep(2000);

        // Verificar el intercambio
        assertEquals(driver.findElement(By.cssSelector("#column-a header")).getText(), "B");
        assertEquals(driver.findElement(By.cssSelector("#column-b header")).getText(), "A");
    }
}
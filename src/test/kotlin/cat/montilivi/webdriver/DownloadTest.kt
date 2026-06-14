package cat.montilivi.webdriver

import org.junit.Assert.assertThat
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.safari.SafariDriver
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test
import sun.nio.cs.Surrogate.`is`
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.nio.file.attribute.BasicFileAttributes
import java.util.*
import java.util.function.ToLongFunction

//Versió Moodle
//class DownloadTest : TestBasic() {
//    var folder: File? = null // Carpeta temporal on es guardaran les descàrregues
//
//    @BeforeMethod
//    fun setUp() {
//        // Crear una carpeta temporal amb nom únic (UUID)
//        folder = File(
//            ("src" + File.separator + "main"
//                    + File.separator + "resources"
//                    + File.separator + UUID.randomUUID().toString())
//        )
//
//        // --- Configuració de Chrome ---
//        val downloadFilepath = folder!!.getAbsolutePath()
//        val chromePrefs = HashMap<String?, Any?>()
//        chromePrefs.put("profile.default_content_settings.popups", 0)
//        chromePrefs.put("download.default_directory", downloadFilepath)
//
//        val options = ChromeOptions()
//        options.setExperimentalOption("prefs", chromePrefs)
//        options.setAcceptInsecureCerts(true)
//
//        driver = ChromeDriver(options)
//    }
//
//    @AfterMethod
//    fun tearDown() {
//        driver.quit()
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun download() {
//        // 1. Crear la carpeta de descàrregues
//        folder!!.mkdir()
//
//        // 2. Navegar a la pàgina de descàrregues
//        driver.get("http://the-internet.herokuapp.com/download")
//        Thread.sleep(2000)
//
//        // 3. Localitzar el primer enllaç de descàrrega i fer clic
//        val actions = Actions(driver)
//        val link: WebElement? = driver.findElement(
//            By.xpath("/html[1]/body[1]/div[2]/div[1]/div[1]/a[1]")
//        )
//        actions.dragAndDrop(link, link).build().perform()
//        Thread.sleep(2000)
//
//        // 4. Verificar que la carpeta no està buida (s'ha descarregat alguna cosa)
//        val listOfFiles = folder!!.listFiles()
//        assertNotEquals(0L, listOfFiles!!.size.toLong())
//
//        // 5. Verificar que els fitxers descarregats no estan buits
//        for (file in listOfFiles) {
//            assertNotEquals(0L, file.length())
//        }
//
//        // 6. Netejar — esborrar els fitxers i la carpeta temporal
//        val entries = folder!!.list()
//        for (s in entries!!) {
//            File(folder!!.getPath(), s).delete()
//        }
//        folder!!.delete()
//    }
//}

//Fet a classe:
class DownloadTest : TestBasic() {
    var folder: File? = null

    fun moureUltimFitxerDescarregat(rutaDestiFinal: String) {
        val rutaDownloadsGeneral = System.getProperty("user.home") + "/Downloads"
        val directori = File(rutaDownloadsGeneral)
        var ultimFitxer: File? = null


        // 1. Espera dinàmica (màxim 15 intents, 1 intent per segon)
        for (i in 0..14) {
            val fitxers = directori.listFiles()

            if (fitxers != null && fitxers.size > 0) {
                ultimFitxer = Arrays.stream<File?>(fitxers)
                    .filter { obj: File? -> obj!!.isFile() }  // GARANTEIX QUE ÉS UN FITXER (NO UNA FOLDER)
                    .filter { f: File? -> !f!!.getName().startsWith(".") }  // Ignora fitxers ocults (.DS_Store)
                    .filter { f: File? -> !f!!.getName().endsWith(".download") }  // Ignora temporals de Safari
                    .max(Comparator.comparingLong<File?>(ToLongFunction comparingLong@{ f: File? ->
                        try {
                            // Llegeix la data exacta de CREACIÓ del fitxer al Mac
                            val attr = Files.readAttributes<BasicFileAttributes?>(
                                f!!.toPath(),
                                BasicFileAttributes::class.java
                            )
                            return@comparingLong attr.creationTime().toMillis()
                        } catch (e: IOException) {
                            return@comparingLong 0L // Si falla, el posa a la cua
                        }
                    }))
                    .orElse(null)
            }


            // Si hem trobat un fitxer real, sortim del bucle d'espera
            if (ultimFitxer != null) {
                break
            }


            // Si no el troba o Safari encara el descarrega, espera 1 segon i torna-ho a intentar
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }

        // 2. Gestionar el fitxer trobat
        if (ultimFitxer == null) {
            System.err.println("Error: No s'ha detectat cap fitxer nou creat a la carpeta Downloads.")
            return
        }

        // 3. Moure el fitxer a la teva destinació final
        try {
            val origen = ultimFitxer.toPath()
            var desti = Paths.get(rutaDestiFinal)

            if (Files.isDirectory(desti)) {
                desti = desti.resolve(ultimFitxer.getName())
            }

            Files.move(origen, desti, StandardCopyOption.REPLACE_EXISTING)
            println("L'últim fitxer CREAT s'ha mogut correctament a: " + desti.toString())
        } catch (e: IOException) {
            System.err.println("Error en moure el fitxer: " + e.message)
        }
    }

    @BeforeMethod
    fun setUp() {
        folder = File(
            ("src" + File.separator + "test"
                    + File.separator + "resources"
                    + File.separator + UUID.randomUUID().toString())
        )

        val downloadFilepath = folder!!.getAbsolutePath()

        if (System.getProperty("browser").equals("safari", ignoreCase = true)) {
            driver = SafariDriver()
        } else if (System.getProperty("browser").equals("firefox", ignoreCase = true)) {
            //Firefox
            // 1. Configurar el perfil de Firefox
            val profile = FirefoxProfile()
            profile.setPreference(
                "browser.helperApps.neverAsk.saveToDisk",
                "application/octet-stream;application/csv;text/csv;application/vnd.ms-excel;"
            )
            profile.setPreference("browser.helperApps.alwaysAsk.force", false)
            profile.setPreference("browser.download.manager.showWhenStarting", false)
            profile.setPreference("browser.download.folderList", 2)
            profile.setPreference("browser.download.dir", downloadFilepath)

            // 2. Usar FirefoxOptions en lugar de DesiredCapabilities
            val options = FirefoxOptions()
            options.setProfile(profile)

            // Opcional: Si necesitas ejecutarlo en modo incógnito o sin interfaz gráfica (headless)
            //options.addArguments("-headless");

            // 3. Inicializar el driver
            // NOTA: Ya no necesitas System.setProperty para el geckodriver.
            driver = FirefoxDriver(options)
        } else {
            //Chrome
            val chromePrefs = HashMap<String?, Any?>()
            chromePrefs.put("profile.default_content_settings.popups", 0)
            chromePrefs.put("download.default_directory", downloadFilepath)
            val options = ChromeOptions()
            options.setExperimentalOption("prefs", chromePrefs)
            options.setAcceptInsecureCerts(true)
            //options.addArguments("--headless");
            driver = ChromeDriver(options)
        }
    }

    @AfterMethod
    fun tearDown() {
        driver.quit()
    }

    @Test
    @Throws(Exception::class)
    fun download() {
        folder!!.mkdir()
        driver.get("http://the-internet.herokuapp.com/download")
        Thread.sleep(2000)
        val actions = Actions(driver)
        val link: WebElement? = driver.findElement(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[1]/a[1]"))
        actions.dragAndDrop(link, link).build().perform()
        // Wait 2 seconds to download file
        Thread.sleep(2000)

        // A Safari no podem tocar les Options i hem de fer aquest "hack" per moure el fitxer descarregat a la carpeta que volem
        if (System.getProperty("browser").equals("safari", ignoreCase = true)) {
            moureUltimFitxerDescarregat(folder!!.getAbsolutePath())
        }

        val listOfFiles = folder!!.listFiles()
        // Make sure the directory is not empty
        assertTrue(listOfFiles!!.isNotEmpty())
        for (file in listOfFiles) {
            // Make sure the downloaded file(s) is(are) not empty
            assertNotEquals(0L, file.length())
        }

        val entries = folder!!.list()
        for (s in entries!!) {
            val currentFile = File(folder!!.getPath(), s)
            currentFile.delete()
        }
        folder!!.delete()
    }
}
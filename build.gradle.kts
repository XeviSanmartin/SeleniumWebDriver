plugins {
    kotlin("jvm") version "2.3.21"
}

group = "cat.montilivi"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // 0. SOLUCIÓ SLF4J: Afegeix un motor de logs bàsic perquè WebDriverManager pugui imprimir informació
    //testImplementation("org.slf4j:slf4j-simple:2.0.13")

    // 1. La llibreria principal de Selenium (s'utilitza la de Java, totalment compatible amb Kotlin)
    testImplementation("org.seleniumhq.selenium:selenium-java:4.18.1")

    // 2. WebDriverManager: Màgia pura per no haver de descarregar el "chromedriver.exe" a mà
    // Substituit pel drivemanager de Selenium
    //testImplementation("io.github.bonigarcia:webdrivermanager:6.1.0")

    // 3. El framework de tests (JUnit 5)
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("org.testng:testng:6.9.6")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // 4. (Opcional) El motor de logs i el connector CDP que vam afegir abans per netejar els avisos de la consola
    testImplementation("org.slf4j:slf4j-simple:2.0.13")
    //testImplementation("org.seleniumhq.selenium:selenium-devtools-v86:4.18.1")

    // 5. Llibreries estàndard de Kotlin per a proves
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(24)
}

tasks.test {
    useJUnitPlatform()

    // Aquesta línia fa de pont: agafa el paràmetre extern i l'injecta al test
    if (System.getProperty("Navegador") != null) {
        systemProperty("Navegador", System.getProperty("Navegador").toString())
    }
}
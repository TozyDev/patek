plugins {
    id("patek.ktlint-conventions") apply false
}

subprojects {
    apply(plugin = "patek.ktlint-conventions")
}

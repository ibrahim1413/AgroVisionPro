package com.example.agrovisionpro.utils


fun removeMapNullValues(
    map: Map<String, String?>
): Map<String, String> {
    return map
        .filterValues { !it.isNullOrBlank() }
        .mapValues { it.value!! }
}
package edu.skillbox.architecture.entity

interface UsefulActivity {
    val activity: String
    val key: String?
    val link: String?
    val participants: Int?
    val price: Double?
    val type: String?
}
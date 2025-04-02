package com.example.contactapp.model

class contact {
    private var id: Int
    private var name: String
    private var phone: String
    private var email: String

    constructor(id: Int, name: String, phone: String, email: String) {
        this.id = id
        this.name = name
        this.phone = phone
        this.email = email
    }

}
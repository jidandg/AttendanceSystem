package com.marwinjidopi.attendancesystem.utils

import com.marwinjidopi.attendancesystem.data.ContentEntity

object DataDummy {
    fun generateDummyData(): ArrayList<ContentEntity> {
        val classData = ArrayList<ContentEntity>()
        classData.add(
            ContentEntity(
                "1",
                "Pemodelan Sistem Multimedia",
                "Dr. Ir. Dodi Sudiana, M.Eng.",
                "Dr.Eng. Mia Rizkinia, ST., MT.",
                "Tuesday",
                "10.00",
                "Mata kuliah Pengolahan Sinyal Multimedia adalah mata kuliah yang diberikan kepada mahasiswa semester 6 Program Studi S1 Teknik Komputer. Setelah mengikuti mata kuliah ini, mahasiswa akan mampu menganalisis program yang menerapkan prinsip teknik kompresi dan manipulasi file multimedia, yaitu suara, gambar dan video. Mahasiswa juga akan mampu menerapkan algoritma pengolahan citra digital untuk menganalisis informasi di dalamnya, serta menggunakan bahasa lisan untuk presentasi permasalahan pemrosesan sinyal multimedia. Bahan kajian yang akan dipelajari dalam perkuliahan ini adalah pengantar jaringan multimedia, pengkodean dan kompresi sinyal multimedia, filtering pada domain spasial dan frekuensi, restorasi citra, pengolahan citra berwarna, operasi morfologi, segmentasi citra, representasi dan deskripsi, pengenalan dan penelusuran obyek."
            )
        )
        classData.add(
            ContentEntity(
                "2",
                "Pemrograman Berorientasi Objek",
                "I Gde Dharma Nugraha, S.T., M.T., Ph.D",
                "Ruki Harwahyu, S.T., M.Sc., M.T., Ph.D.",
                "Thursday",
                "07.00",
                "Mata kuliah Pemrograman Berorientasi Objek dan Praktik (OOP+P) adalah mata kuliah yang diberikan untuk mahasiswa semester 6 program studi S1 Teknik Komputer. Mata kuliah ini merupakan mata kuliah lanjutan yang diberikan setelah mengikuti Pemrograman Lanjut kepada mahasiswa untuk mendukung 2 Capaian Pembelajaran Prodi, yakni: (1) mampu merancang algoritma untuk masalah tertentu dan mengimplementasikannya ke dalam pemrograman; dan (2) mampu memanfaatkan teknologi informasi komunikasi." + "Pada mata kuliah ini akan dipelajari paradigma dalam pemrograman yang menganggap semua komponen yang ada di dalam suatu program sebagai objek, layaknya di dunia nyata. Konsep ini sangat penting dalam pengembangan perangkat lunak dengan kompleksitas yang lebih tinggi, mempermudah kerja sama tim, serta perawatan (maintenance) dan pengembangan perangkat lunak selanjutnya." +
                        "Pada kuliah ini akan dipelajari cara membuat program dengan konsep berorientasi objek. Setelah mengikuti kuliah ini mahasiswa mengimplementasikan rancangan perangkat lunak ke dalam bahasa pemrograman berorientasi objek; Mampu mendeklarasikan konsep pemrograman berorientasi objek (class, constructor, scope of variables); Mampu menjabarkan objek-objek dasar (array, arry list, koleksi objek, iterator); mampu menjabarkan konsep perancangan class (coupling, kohesi, refactroing, inheritance, polymorph, subtitusi); mampu menerapkan pemrograman berbasis GUI, exception handling dan multithreading."
            )
        )
        classData.add(
            ContentEntity(
                "3",
                "Rekayasa Perangkat Lunak",
                "Prof. Dr. Ir. Riri Fitri Sari MM MSc",
                null.toString(),
                "Wednesday",
                "13.00",
                "Learn about object-oriented software design and software life cycle. At the completion of the subject, students are expected to be able to design a software using UML diagram and implement the software life cycle."
            )
        )
        return classData
    }
}
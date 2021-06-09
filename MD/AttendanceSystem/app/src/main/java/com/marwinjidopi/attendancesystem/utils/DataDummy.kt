package com.marwinjidopi.attendancesystem.utils

import com.marwinjidopi.attendancesystem.data.entity.ClassEntity
import com.marwinjidopi.attendancesystem.data.entity.ContentEntity

object DataDummy {
    fun generateDummyData(): ArrayList<ContentEntity> {
        val classData = ArrayList<ContentEntity>()
        classData.add(
            ContentEntity(
                "1",
                "Computer Engineering Project Design 1 01",
                "Fransiskus Astha Ekadiyanto",
                "Yan Maraden, S.T., M.T.",
                "Friday",
                "16:00 - 17:40",
                "This course will discuss managerial principles in IT projects. After attending this lecture, students are expected to be able to implement project management, including team management, scheduling, project configuration, information management, and project plan design."
            )
        )
        classData.add(
            ContentEntity(
                "2",
                "Object Oriented Programming and Laboratory-01",
                "I Gde Dharma Nugraha, S.T., M.T., Ph.D.",
                "Ruki Harwahyu, S.T., M.Sc., M.T., Ph.D.",
                "Thursday",
                "08:00 - 09:40",
                "In this course, students will learn how to create programs with object-oriented concepts. After completing the course, students will be able to implement the design of software into the languages of oriented object programming; Being able to declare the concept of oriented object programming (class, constructor, scope of variables); Able to describe basic objects (arrays, array lists, object collections, iterators); able to describe the concept of class design (coupling, cohesion, refactoring, inheritance, polymorph, substitution); able to implement GUI-based programming, exception handling and multithreading."
            )
        )
        classData.add(
            ContentEntity(
                "3",
                "Multimedia Signal Processing-01",
                "Dr. Dodi Sudiana, M.Eng.",
                "Mia Rizkinia, S.T., M.T.",
                "Tuesday",
                "10:00 - 12:30",
                "In this course students will learn multimedia signal processing technology to support the delivery of multimedia information through the Internet. At the end of this course, the student will be able to perform analysis of multimedia signals in the network using appropriate techniques. Students will be able to describe components in multimedia files, multimedia compression techniques, are able to perform analysis and processing of multimedia data such as image, sound and video. Students will also be able to apply a digital image processing algorithm to analyze the information in it."
            )
        )
        return classData
    }

    fun generateLastClassDummyData(): ArrayList<ClassEntity>{
        val classInfo = ArrayList<ClassEntity>()
        classInfo.add(
            ClassEntity(
                "1",
                "Computer Engineering Project Design 1 01",
                "https://emas2.ui.ac.id/course/view.php?id=4216",
                "Friday, June 4, 2021",
                "16:00 - 17:40"
            )
        )
        classInfo.add(
            ClassEntity(
                "2",
                "Object Oriented Programming and Laboratory-01",
                "https://emas.ui.ac.id/course/view.php?id=13248",
                "Thursday, June 3, 2021",
                "08:00 - 09:40"
            )
        )
        classInfo.add(
            ClassEntity(
                "3",
                "Multimedia Signal Processing-01",
                "https://emas.ui.ac.id/course/view.php?id=12874",
                "Tuesday, June 1, 2021",
                "10:00 - 12:30",
            )
        )
        return classInfo
    }

    fun generateOngoingClassDummyData(): ArrayList<ClassEntity>{
        val classInfo = ArrayList<ClassEntity>()
        classInfo.add(
            ClassEntity(
                "1",
                "Computer Engineering Project Design 1 01",
                "https://emas2.ui.ac.id/course/view.php?id=4216",
                "Friday, June 11, 2021",
                "16:00 - 17:40"
            )
        )
        classInfo.add(
            ClassEntity(
                "2",
                "Object Oriented Programming and Laboratory-01",
                "https://emas.ui.ac.id/course/view.php?id=13248",
                "Thursday, June 10, 2021",
                "08:00 - 09:40"
            )
        )
        classInfo.add(
            ClassEntity(
                "3",
                "Multimedia Signal Processing-01",
                "https://emas.ui.ac.id/course/view.php?id=12874",
                "Tuesday, June 8, 2021",
                "10:00 - 12:30",
            )
        )
        return classInfo
    }

    fun generateNextClassDummyData(): ArrayList<ClassEntity>{
        val classInfo = ArrayList<ClassEntity>()
        classInfo.add(
            ClassEntity(
                "1",
                "Computer Engineering Project Design 1 01",
                "https://emas2.ui.ac.id/course/view.php?id=4216",
                "Friday, June 18, 2021",
                "16:00 - 17:40"
            )
        )
        classInfo.add(
            ClassEntity(
                "2",
                "Object Oriented Programming and Laboratory-01",
                "https://emas.ui.ac.id/course/view.php?id=13248",
                "Thursday, June 17, 2021",
                "08:00 - 09:40"
            )
        )
        classInfo.add(
            ClassEntity(
                "3",
                "Multimedia Signal Processing-01",
                "https://emas.ui.ac.id/course/view.php?id=12874",
                "Tuesday, June 15, 2021",
                "10:00 - 12:30",
            )
        )
        return classInfo
    }
}

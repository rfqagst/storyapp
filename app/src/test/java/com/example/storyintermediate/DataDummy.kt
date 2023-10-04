package com.example.storyintermediate

import com.example.storyintermediate.api.response.ListStoryItem

object DataDummy {

    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val stories: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..15) {
            val story = ListStoryItem(
                i.toString(),
                "https://story-api.dicoding.dev/images/stories/photos-1667058715359_iRitxc-I.jpg",
                "rifqi",
                "Ini tess",
                37.4220936f.toDouble(),
                -122.083922f.toDouble(),
            )
            stories.add(story)
        }
        return stories
    }
}
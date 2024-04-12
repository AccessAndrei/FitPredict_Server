package com.ortin.models

import kotlinx.serialization.Serializable

@Serializable
data class FilesInfo(
    val modelName: String? = null,
    val modelVersion: Int = 0,
    val imageName: String? = null,
    val imageVersion: Int = 0,
    val videoName: String? = null,
    val videoVersion: Int = 0,
)

fun FilesInfo.merge(otherFilesInfo: FilesInfo): FilesInfo =
    FilesInfo(
        modelName = otherFilesInfo.modelName ?: modelName,
        modelVersion = if (modelVersion >= otherFilesInfo.modelVersion) modelVersion else otherFilesInfo.modelVersion,
        imageName = otherFilesInfo.imageName ?: imageName,
        imageVersion = if (imageVersion >= otherFilesInfo.imageVersion) imageVersion else otherFilesInfo.imageVersion,
        videoName = otherFilesInfo.videoName ?: videoName,
        videoVersion = if (videoVersion >= otherFilesInfo.videoVersion) videoVersion else otherFilesInfo.videoVersion,
    )

package net.xxs.service;

import java.io.File;

import net.xxs.bean.CardsImage;


/**
 * Service接口 - 充值卡图片
 */

public interface CardsImageService {
	
	/**
	 * 生成充值卡图片（大图、小图、缩略图）
	 * 
	 * @param cardsImageFile
	 *            图片文件
	 * 
	 */
	public CardsImage buildCardsImage(File cardsImageFile);

}
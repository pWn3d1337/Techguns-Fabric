package techguns.api.client.entity;

import techguns.api.entity.ITGExtendedPlayer;

public interface ITGExtendedPlayerClient extends ITGExtendedPlayer{

	public int getSwingSoundDelay();
	public void setSwingSoundDelay(int delay);
}

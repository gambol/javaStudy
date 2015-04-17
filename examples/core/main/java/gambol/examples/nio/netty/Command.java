package gambol.examples.nio.netty;

import java.io.Serializable;

/**
 * User: zhenbao.zhou
 * Date: 4/17/15
 * Time: 5:55 PM
 */
public class Command implements Serializable{
    private static final long serialVersionUID = 7590999461767050471L;

    private String actionName;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
}

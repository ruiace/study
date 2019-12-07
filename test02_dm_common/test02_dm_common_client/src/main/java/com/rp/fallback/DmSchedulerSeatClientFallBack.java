package com.rp.fallback;

import com.rp.pojo.DmSchedulerSeat;

import com.rp.client.RestDmSchedulerSeatClient;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
@Component
public class DmSchedulerSeatClientFallBack implements RestDmSchedulerSeatClient {


    @Override
    public DmSchedulerSeat getDmSchedulerSeatById(Long id)throws Exception{
        return null;
    }

    @Override
    public List<DmSchedulerSeat>	getDmSchedulerSeatListByMap(Map<String,Object> param)throws Exception{
        return null;
    }

    @Override
    public Integer getDmSchedulerSeatCountByMap(Map<String,Object> param)throws Exception{
        return null;
    }

    @Override
    public Integer qdtxAddDmSchedulerSeat(DmSchedulerSeat dmSchedulerSeat)throws Exception{
        return null;
    }

    @Override
    public Integer qdtxModifyDmSchedulerSeat(DmSchedulerSeat dmSchedulerSeat)throws Exception{
        return null;
    }
}

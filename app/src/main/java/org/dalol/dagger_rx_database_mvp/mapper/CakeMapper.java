/*
 * Copyright (c) 2016 Filippo Engidashet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dalol.dagger_rx_database_mvp.mapper;

import org.dalol.dagger_rx_database_mvp.mvp.model.Cake;
import org.dalol.dagger_rx_database_mvp.mvp.model.CakesResponse;
import org.dalol.dagger_rx_database_mvp.mvp.model.CakesResponseCakes;
import org.dalol.dagger_rx_database_mvp.mvp.model.Storage;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 9/24/2016
 */
public class CakeMapper {

    @Inject
    public CakeMapper() {
    }

    public List<Cake> mapCakes(Storage storage, CakesResponse response) {
        List<Cake> cakeList = new ArrayList<>();

        if (response != null) {
            CakesResponseCakes[] responseCakes = response.getCakes();
            if (responseCakes != null) {
                for (CakesResponseCakes cake : responseCakes) {
                    Cake myCake = new Cake();
                    myCake.setId(cake.getId());
                    myCake.setTitle(cake.getTitle());
                    myCake.setDetailDescription(cake.getDetailDescription());
                    myCake.setPreviewDescription(cake.getPreviewDescription());
                    myCake.setImageUrl(cake.getImage());
                    storage.addCake(myCake);
                    cakeList.add(myCake);
                }
            }
        }
        return cakeList;
    }
}
